import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardMarketCards } from './dashboard-market-cards.component';

const meta: Meta<DashboardMarketCards> = {
  title: 'Components/DashboardMarketCards',
  component: DashboardMarketCards,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardMarketCards>;

export const Primary: Story = {
  args: {},
};
