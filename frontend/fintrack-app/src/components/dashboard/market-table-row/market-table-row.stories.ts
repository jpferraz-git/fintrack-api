import type { Meta, StoryObj } from '@storybook/angular';
import { MarketTableRowComponent } from './market-table-row.component';

const meta: Meta<MarketTableRowComponent> = {
  title: 'Components/MarketTableRowComponent',
  component: MarketTableRowComponent,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<MarketTableRowComponent>;

export const Primary: Story = {
  args: {},
};
