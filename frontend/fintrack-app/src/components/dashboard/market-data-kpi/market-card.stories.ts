import type { Meta, StoryObj } from '@storybook/angular';
import { MarketCardComponent } from './market-card.component';

const meta: Meta<MarketCardComponent> = {
  title: 'Components/MarketCardComponent',
  component: MarketCardComponent,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<MarketCardComponent>;

export const Primary: Story = {
  args: {},
};
