import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardChartSymbolSelectorComponent } from './dashboard-chart-symbol-selector.component';

const meta: Meta<DashboardChartSymbolSelectorComponent> = {
  title: 'Components/DashboardChartSymbolSelectorComponent',
  component: DashboardChartSymbolSelectorComponent,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardChartSymbolSelectorComponent>;

export const Primary: Story = {
  args: {},
};
